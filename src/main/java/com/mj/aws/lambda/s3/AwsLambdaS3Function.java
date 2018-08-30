package com.mj.aws.lambda.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component("awsLambdaS3Function")
public class AwsLambdaS3Function implements Function<SampleInput,SampleOutput> {

    @Autowired
    private AmazonS3 amazonS3;

//    @Autowired
//    private TestEureka testEureka;

    @Value("${testValue:empty}")
    private String configValue;

    @Override
    public SampleOutput apply(SampleInput sampleInput) {

        SampleOutput sampleOutput = new SampleOutput();
        StringBuilder sb = new StringBuilder();

        log.info(String.format("What is configValue? %s", configValue));

        log.info(String.format("S3 Event processing starts with record: %s %s",
                sampleInput.getBucketName(), sampleInput.getBucketKey()));

        String s3Bucket = sampleInput.getBucketName();
        String s3Key = sampleInput.getBucketKey();

        log.info(String.format("Received record with bucket: %s and key:  %s", s3Bucket ,s3Key));

        S3Object object = amazonS3.getObject(new GetObjectRequest(s3Bucket, s3Key));

        log.info("Retrieved s3 object: {} ", object);
        sb.append("Successfully retrieved: ");
        sb.append(object.toString().length());

//        log.info("Attempting to connect to Eureka");
//
//        sb.append(testEureka.getTASMessage());
//
//        log.info("Eureka API call completed!");

        sampleOutput.setOutput(sb.toString());

        return sampleOutput;
    }
}
