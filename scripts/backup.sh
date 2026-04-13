#!/bin/bash
# Blog 백업 스크립트
# - PostgreSQL dump (.sql.gz) + SHA256 체크섬
# - /uploads/ 이미지 폴더
# - Google Drive (rclone) 업로드
# - 로컬 30일 이상 파일 자동 삭제

set -euo pipefail

# ── 설정 ───────────────────────────────────────────────────
BLOG_DIR="/home/jeremy/dev/blog"
BACKUP_DIR="$BLOG_DIR/backups"
UPLOADS_DIR="$BLOG_DIR/uploads"
ENV_FILE="$BLOG_DIR/backend/.env"

RCLONE_REMOTE="gdrive:blog-backup"        # rclone remote 이름
KEEP_DAYS=30                               # 로컬 보관 기간

DATE=$(date +%Y%m%d_%H%M%S)
DUMP_FILE="$BACKUP_DIR/blog_${DATE}.sql.gz"
CHECKSUM_FILE="${DUMP_FILE}.sha256"

# ── .env에서 DB 정보 로드 ──────────────────────────────────
if [[ -f "$ENV_FILE" ]]; then
  export $(grep -v '^#' "$ENV_FILE" | xargs)
fi

# jdbc:postgresql://localhost:5432/blog → localhost / blog 파싱
DB_HOST_RAW="${DB_HOST#jdbc:postgresql://}"   # localhost:5432/blog
DB_HOST_PORT="${DB_HOST_RAW%%/*}"             # localhost:5432
DB_HOSTNAME="${DB_HOST_PORT%%:*}"             # localhost
DB_PORT="${DB_HOST_PORT##*:}"                 # 5432
DB_NAME="${DB_HOST_RAW##*/}"                  # blog

LOG_PREFIX="[$(date '+%Y-%m-%d %H:%M:%S')] [backup]"

echo "$LOG_PREFIX 시작 — $DATE"

# ── 1. SQL dump ────────────────────────────────────────────
mkdir -p "$BACKUP_DIR"
echo "$LOG_PREFIX pg_dump 시작..."

PGPASSWORD="$DB_PASSWORD" pg_dump \
  -h "$DB_HOSTNAME" \
  -p "$DB_PORT" \
  -U "$DB_USERNAME" \
  "$DB_NAME" | gzip > "$DUMP_FILE"

echo "$LOG_PREFIX dump 완료: $DUMP_FILE ($(du -sh "$DUMP_FILE" | cut -f1))"

# ── 2. SHA256 체크섬 생성 ──────────────────────────────────
sha256sum "$DUMP_FILE" > "$CHECKSUM_FILE"
echo "$LOG_PREFIX 체크섬: $(cat "$CHECKSUM_FILE")"

# ── 3. Google Drive 업로드 ─────────────────────────────────
echo "$LOG_PREFIX Google Drive 업로드 시작..."

# SQL dump + 체크섬
rclone copy "$DUMP_FILE"      "${RCLONE_REMOTE}/dumps/" --log-level INFO
rclone copy "$CHECKSUM_FILE"  "${RCLONE_REMOTE}/dumps/" --log-level INFO

# 이미지 업로드 폴더 동기화 (삭제 없이 추가만)
if [[ -d "$UPLOADS_DIR" ]]; then
  rclone copy "$UPLOADS_DIR/" "${RCLONE_REMOTE}/uploads/" --log-level INFO
  echo "$LOG_PREFIX uploads 동기화 완료"
fi

echo "$LOG_PREFIX Google Drive 업로드 완료"

# ── 4. 로컬 오래된 파일 정리 ──────────────────────────────
find "$BACKUP_DIR" -name "*.sql.gz"    -mtime +${KEEP_DAYS} -delete
find "$BACKUP_DIR" -name "*.sha256"    -mtime +${KEEP_DAYS} -delete
echo "$LOG_PREFIX ${KEEP_DAYS}일 이상 로컬 파일 삭제 완료"

echo "$LOG_PREFIX 백업 완료"
