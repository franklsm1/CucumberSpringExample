package com.example.stepDefinitions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@SpringBootTest
@CucumberOptions(features = "src/test/resources/",
        glue = {"cucumber.examples.spring.txn","cucumber.api.spring","com.example.stepDefinitions"})
public class CucumberTest {

}
