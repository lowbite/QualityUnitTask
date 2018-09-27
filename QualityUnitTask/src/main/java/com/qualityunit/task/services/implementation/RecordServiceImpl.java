package com.qualityunit.task.services.implementation;

import com.qualityunit.task.io.RecordsReader;
import com.qualityunit.task.io.ResultTimeWriter;
import com.qualityunit.task.model.Query;
import com.qualityunit.task.model.Reply;
import com.qualityunit.task.services.RecordService;

import java.util.List;
import java.util.stream.Collectors;

public class RecordServiceImpl implements RecordService {
    private RecordsReader recordsReader;
    private ResultTimeWriter resultTimeWriter;

    public RecordServiceImpl() {
    }

    public void setRecordsReader(RecordsReader recordsReader) {
        this.recordsReader = recordsReader;
    }

    public void setResultTimeWriter(ResultTimeWriter resultTimeWriter) {
        this.resultTimeWriter = resultTimeWriter;
    }

    public List<String> readRecords() {
        return recordsReader.getRecords();
    }

    public List<String> getRecordsByRegex(List<String> records, String regex) {
        return records.stream().filter(s -> s.matches(regex)).collect(Collectors.toList());
    }

    public void writeResult(List<Integer> result) {
        resultTimeWriter.writeTime(result);
    }

    public List<Integer> getAverageWaitingTimeByQueries(List<Reply> records, List<String> queries) {
        return queries.stream().map(
                s -> getAverageWaitingTimeByQuery(records, s)
        ).collect(Collectors.toList());
    }

    public int getAverageWaitingTimeByQuery(List<Reply> records, String queryText) {
        Query query = Query.fromString(queryText);
        List<Reply> filteredRecords = records.stream()
                .filter(reply -> {
                    if (query.getService() != null) {
                        if (query.getService().getServiceId() != 0 && reply.getService().getServiceId() != query.getService().getServiceId())
                            return false;
                        if (query.getService().getVariationId() != 0 && reply.getService().getVariationId() != query.getService().getVariationId())
                            return false;
                    }
                    return true;
                })
                .filter(reply -> {
                    if (query.getQuestion() != null) {
                        if (query.getQuestion().getQuestionTypeId() != 0 && reply.getQuestion().getQuestionTypeId() != query.getQuestion().getQuestionTypeId())
                            return false;
                        if (query.getQuestion().getCategoryId() != 0 && reply.getQuestion().getCategoryId() != query.getQuestion().getCategoryId())
                            return false;
                        if (query.getQuestion().getSubcategoryId() != 0 && reply.getQuestion().getSubcategoryId() != query.getQuestion().getSubcategoryId())
                            return false;
                    }
                    return true;
                })
                .filter(reply -> {
                    if (query.getResponseType() == null)
                        throw new NullPointerException("Empty response type");
                    return query.getResponseType().equals(reply.getResponseType());
                })
                .filter(reply -> query.getDateFrom() <= reply.getDate())
                .filter(reply -> query.getDateTo() == 0 || query.getDateTo() >= reply.getDate())
                .collect(Collectors.toList());
        long waitingTimeSum = 0;
        for (Reply reply : filteredRecords) {
            waitingTimeSum += reply.getWaitingTime();
        }
        return filteredRecords.size() != 0 ? (int) waitingTimeSum / filteredRecords.size() : 0;
    }
}
