vi produce-batch.sh

#!/bin/bash

# Kafka 서버 설정
bootstrap_servers="localhost:9092"
topic="test-topic"

# 무작위 단어 리스트
words=("apple" "banana" "orange" "grape" "melon" "kiwi" "strawberry" "pineapple" "peach" "plum")

# 스크립트 실행 여부
running=true

# Kafka 프로듀서 실행
echo "Producer started. Press any key to stop..."
while $running; do
    now=$(date +"%H%M%S")
    hour=$(date +"%H")

    if [ $now -ge 000000 ] && [ $now -lt 010000 ]; then
        number=$(( ( RANDOM % 10000 ) + 1 ))
        for (( i=1; i<=$number; i++ )); do
            random_word=${words[$((RANDOM % ${#words[@]}))]}
            echo "Sent message: $number -> $random_word"
            echo "$hour $random_word" | kafka-console-producer.sh --broker-list $bootstrap_servers --topic $topic >/dev/null 2>&1
        done
    fi

    sleep 1
done

# 스크립트 중단 시 Kafka 프로듀서 종료
echo "Producer stopped."