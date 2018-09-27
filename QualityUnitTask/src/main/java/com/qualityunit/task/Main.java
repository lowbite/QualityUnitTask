package com.qualityunit.task;

import com.qualityunit.task.constants.Regex;
import com.qualityunit.task.io.implementation.FileRecordsReader;
import com.qualityunit.task.io.implementation.FileResultTimeWriter;
import com.qualityunit.task.model.Reply;
import com.qualityunit.task.services.implementation.RecordServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args){
        RecordServiceImpl recordServiceImpl = new RecordServiceImpl();
        recordServiceImpl.setRecordsReader(new FileRecordsReader("src\\main\\resources\\records.data"));
        recordServiceImpl.setResultTimeWriter(new FileResultTimeWriter("src\\main\\resources\\queries_result.data"));

        List<String> records = recordServiceImpl.readRecords();
        List<String> replyRecords = recordServiceImpl.getRecordsByRegex(records, Regex.REPLY);
        List<String> queryRecords = recordServiceImpl.getRecordsByRegex(records, Regex.QUERY);
        List<Reply> replies = replyRecords.stream()
                .map(Reply::fromString)
                .collect(Collectors.toList());
        List<Integer> queriesResult = recordServiceImpl.getAverageWaitingTimeByQueries(replies, queryRecords);
        recordServiceImpl.writeResult(queriesResult);
    }
}
