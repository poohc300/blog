#!/bin/bash
# 백업 파일 무결성 검증 스크립트
# 사용법:
#   ./verify_backup.sh blog_20260413_020000.sql.gz
#   ./verify_backup.sh                              # backups/ 에서 최신 파일 자동 선택

set -euo pipefail

BACKUP_DIR="/home/jeremy/dev/blog/backups"

# 파일 인자가 없으면 최신 파일 자동 선택
if [[ $# -eq 0 ]]; then
  DUMP_FILE=$(ls -t "$BACKUP_DIR"/*.sql.gz 2>/dev/null | head -1)
  if [[ -z "$DUMP_FILE" ]]; then
    echo "[오류] $BACKUP_DIR 에 백업 파일이 없습니다."
    exit 1
  fi
  echo "[자동 선택] $DUMP_FILE"
else
  DUMP_FILE="$1"
  # 경로 없이 파일명만 넘겨도 동작
  if [[ ! -f "$DUMP_FILE" ]]; then
    DUMP_FILE="$BACKUP_DIR/$1"
  fi
fi

CHECKSUM_FILE="${DUMP_FILE}.sha256"

if [[ ! -f "$DUMP_FILE" ]]; then
  echo "[오류] 파일 없음: $DUMP_FILE"
  exit 1
fi

if [[ ! -f "$CHECKSUM_FILE" ]]; then
  echo "[오류] 체크섬 파일 없음: $CHECKSUM_FILE"
  exit 1
fi

echo "검증 대상: $DUMP_FILE"
echo "체크섬:    $(cat "$CHECKSUM_FILE")"
echo ""

if sha256sum -c "$CHECKSUM_FILE" --quiet; then
  echo "[OK] 파일 무결성 검증 통과"
else
  echo "[FAIL] 파일이 손상되었거나 변조되었습니다!"
  exit 1
fi
