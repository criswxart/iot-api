package com.tld.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tld.model.Metric;

public interface MetricRepository extends JpaRepository<Metric, Integer> {

}
