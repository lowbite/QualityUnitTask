package com.qualityunit.task.services;

import com.qualityunit.task.model.Reply;

import java.util.List;

public interface RecordService {

    List<String> readRecords();

    void writeResult(List<Integer> result);

    List<String> getRecordsByRegex(List<String> records, String regex);

    List<Integer> getAverageWaitingTimeByQueries(List<Reply> records, List<String> queries);

    int getAverageWaitingTimeByQuery(List<Reply> records, String queryText);
}
