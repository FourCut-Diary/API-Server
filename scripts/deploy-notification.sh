#!/bin/bash
cd /home/ec2-user/fourcut-diary

MODULE_NAME="fourcut-diary-notification"
CONTAINER_NAME="${MODULE_NAME}"
PORT=8082

source ./scripts/health_check.sh
source ./scripts/deploy_container.sh
source ./scripts/stop_container.sh

echo "📦 배포 시작: ${CONTAINER_NAME} (port: ${PORT})"

# 기존 컨테이너 종료
stop_container ${CONTAINER_NAME}

# 새 컨테이너 배포
deploy_container ${CONTAINER_NAME} ${PORT}

# 헬스 체크
if ! health_check ${PORT}; then
  echo "❌ Health check failed"
  stop_container ${CONTAINER_NAME}
  exit 1
fi

echo "✅ ${CONTAINER_NAME} 배포 완료"