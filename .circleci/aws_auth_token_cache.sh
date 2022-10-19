#!/bin/bash

CURRENT_DATE=$(date -u +"%s")

aws s3api get-object --bucket ${CODEARTIFICAT_BUCKET_NAME} --key keys/code_artifact_token.txt code_artifact_token.txt

if [ ! -f "code_artifact_token.txt" ]; then
    CODEARTIFACT_AUTH_TOKEN=`aws codeartifact get-authorization-token --domain $CODE_ARTIFACT_DOMAIN --domain-owner $CODE_ARTIFACT_DOMAIN_OWNER --query authorizationToken --output text`
    echo "$CODEARTIFACT_AUTH_TOKEN" > code_artifact_token.txt
    echo "$CURRENT_DATE" >> code_artifact_token.txt
    aws s3api put-object --bucket ${CODEARTIFICAT_BUCKET_NAME} --key keys/code_artifact_token.txt --body code_artifact_token.txt
else
    DATE_TOKEN_GENERATE=`tail -n 1 code_artifact_token.txt`
    DIFF_DATE=`date -u -d "0 $CURRENT_DATE sec - $DATE_TOKEN_GENERATE sec" +"%H:%M:%S"`
    echo "Date token generated sec $DATE_TOKEN_GENERATE"
    echo "Current date sec $CURRENT_DATE"
    echo "Diff Between current date and token generated date is $DIFF_DATE"
    DIFF_DATE_HOURS=`echo $DIFF_DATE | cut -d: -f1`
    echo "Diff in hours is $DIFF_DATE_HOURS"
    if [ $DIFF_DATE_HOURS -ge 23 ]; then
        CODEARTIFACT_AUTH_TOKEN=`aws codeartifact get-authorization-token --domain $CODE_ARTIFACT_DOMAIN --domain-owner $CODE_ARTIFACT_DOMAIN_OWNER --query authorizationToken --output text`
        echo "$CODEARTIFACT_AUTH_TOKEN" > code_artifact_token.txt
        echo "$CURRENT_DATE" >> code_artifact_token.txt
        aws s3api put-object --bucket ${CODEARTIFICAT_BUCKET_NAME} --key keys/code_artifact_token.txt --body code_artifact_token.txt      
    fi
fi