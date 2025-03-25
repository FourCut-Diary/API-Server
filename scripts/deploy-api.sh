#!/bin/bash
cd /home/ec2-user/fourcut-diary

# 모듈 이름 설정
MODULE_NAME="fourcut-diary-api"
ALL_PORTS=("8080" "8081")

# 스크립트 불러오기
source ./scripts/health_check.sh
source ./scripts/deploy_container.sh
source ./scripts/nginx_reload.sh
source ./scripts/stop_container.sh

# 현재 실행 중인 컨테이너 확인
AVAILABLE_PORT=()
DOCKER_PS_OUTPUT=$(docker ps | grep ${MODULE_NAME})
RUNNING_CONTAINER_NAME=$(echo "$DOCKER_PS_OUTPUT" | awk '{print $NF}')
RUNNING_SERVER_PORT=""

check_running_container() {
  if [[ ${RUNNING_CONTAINER_NAME} =~ "${MODULE_NAME}-blue" ]]; then
    echo "Running: ${MODULE_NAME}-blue (:8080)"
    RUNNING_SERVER_PORT=8080
  elif [[ ${RUNNING_CONTAINER_NAME} =~ "${MODULE_NAME}-green" ]]; then
    echo "Running: ${MODULE_NAME}-green (:8081)"
    RUNNING_SERVER_PORT=8081
  else
    echo "Running: None"
  fi
}

get_available_ports() {
  for item in "${ALL_PORTS[@]}"; do
    if [ "$item" != "${RUNNING_SERVER_PORT}" ]; then
      AVAILABLE_PORT+=("$item")
    fi
  done
}

check_running_container
get_available_ports

if [ ${#AVAILABLE_PORT[@]} -eq 0 ]; then
  echo "No available ports."
  exit 1
fi

# Green Up
if [ "${RUNNING_SERVER_PORT}" == "8080" ]; then
  CURRENT_PORT=8081
  deploy_container "${MODULE_NAME}-green" ${CURRENT_PORT}

  if ! health_check ${CURRENT_PORT}; then
    stop_container "${MODULE_NAME}-green"
    exit 1
  fi

  reload_nginx ${CURRENT_PORT}
  stop_container "${MODULE_NAME}-blue"

# Blue Up
else
  CURRENT_PORT=8080
  deploy_container "${MODULE_NAME}-blue" ${CURRENT_PORT}

  if ! health_check ${CURRENT_PORT}; then
    stop_container "${MODULE_NAME}-blue"
    exit 1
  fi

  reload_nginx ${CURRENT_PORT}
  stop_container "${MODULE_NAME}-green"
fi

# nginx를 통한 최종 헬스체크
if ! health_check ${CURRENT_PORT}; then
  echo "❌ Final health check failed"
  exit 1
fi

echo "✅ ${MODULE_NAME} 배포 완료"