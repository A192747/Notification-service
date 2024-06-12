package ru.micro.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Random;

public class CustomPartitioner implements Partitioner {
    private static Integer max; // Поле для хранения количества партиций
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        Random random = new Random();
        int min = 0; // Нижняя граница
        int randValue = random.nextInt(max) + min;
        return randValue;
    }

    public static void setMax(int newMax) {
        max = newMax;
    }
    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
