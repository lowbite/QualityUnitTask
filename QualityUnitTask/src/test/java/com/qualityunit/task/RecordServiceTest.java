package com.qualityunit.task;

import com.qualityunit.task.constants.Regex;
import com.qualityunit.task.constants.ResponseType;
import com.qualityunit.task.io.RecordsReader;
import com.qualityunit.task.io.ResultTimeWriter;
import com.qualityunit.task.io.implementation.FileRecordsReader;
import com.qualityunit.task.io.implementation.FileResultTimeWriter;
import com.qualityunit.task.model.Question;
import com.qualityunit.task.model.Reply;
import com.qualityunit.task.model.Service;
import com.qualityunit.task.services.RecordService;
import com.qualityunit.task.services.implementation.RecordServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class RecordServiceTest {
    public RecordServiceImpl recordService;
    public List<String> records;
    public List<Reply> replies;

    @Parameterized.Parameter(0)
    public String queryText;
    @Parameterized.Parameter(1)
    public int resultAverageTime;

    public RecordServiceTest() throws ParseException {
        this.recordService = new RecordServiceImpl();
        String[] records = {
                "C 1.1 8.15.1 P 15.10.2012 83",
                "C 1 10.1 P 01.12.2012 65",
                "C 1.1 5.5.1 P 01.11.2012 117",
                "D 1.1 8 P 01.01.2012-01.12.2012",
                "C 3 10.2 N 02.10.2012 100",
                "D 1 * P 8.10.2012-20.11.2012",
                "D 3 10 P 01.12.2012"
        };
        this.records = Arrays.asList(records);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Reply reply1 = new Reply.Builder().setService(new Service(1, 1))
                .setQuestion(new Question(8, 15, 1))
                .setResponseType(ResponseType.P)
                .setDate(formatter.parse("15.10.2012").getTime())
                .setWaitingTime(83)
                .build();
        Reply reply2 = new Reply.Builder().setService(new Service(1, 0))
                .setQuestion(new Question(10, 1, 0))
                .setResponseType(ResponseType.P)
                .setDate(formatter.parse("01.12.2012").getTime())
                .setWaitingTime(65)
                .build();
        Reply reply3 = new Reply.Builder().setService(new Service(1, 1))
                .setQuestion(new Question(5, 5, 1))
                .setResponseType(ResponseType.P)
                .setDate(formatter.parse("01.11.2012").getTime())
                .setWaitingTime(117)
                .build();
        Reply reply4 = new Reply.Builder().setService(new Service(3, 0))
                .setQuestion(new Question(10, 2, 0))
                .setResponseType(ResponseType.N)
                .setDate(formatter.parse("02.10.2012").getTime())
                .setWaitingTime(100)
                .build();
        this.replies = Arrays.asList(reply1, reply2, reply3, reply4);

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = {
                {"D 1.1 8 P 01.01.2012-01.12.2012", 83},
                {"D 1 * P 8.10.2012-20.11.2012", 100},
                {"D 3 10 P 01.12.2012", 0}
        };
        return Arrays.asList(data);
    }

    @Test
    public void recordServiceShouldReadRecords() {
        RecordsReader recordsReader = mock(FileRecordsReader.class);
        recordService.setRecordsReader(recordsReader);
        recordService.readRecords();
        verify(recordsReader).getRecords();
    }

    @Test
    public void recordServiceShouldWriteResult() {
        ResultTimeWriter resultTimeWriter = mock(FileResultTimeWriter.class);
        recordService.setResultTimeWriter(resultTimeWriter);
        recordService.writeResult(null);
        verify(resultTimeWriter).writeTime(null);
    }

    @Test
    public void givenRecordsWhenFilteringRecordsThenReplyRecords() {
        List<String> replies = recordService.getRecordsByRegex(records, Regex.REPLY);
        for (String reply : replies) {
            assert reply.matches(Regex.REPLY);
        }
    }

    @Test
    public void givenRecordsWhenFilteringRecordsThenQueryRecords() {
        List<String> replies = recordService.getRecordsByRegex(records, Regex.QUERY);
        for (String reply : replies) {
            assert reply.matches(Regex.QUERY);
        }
    }

    @Test
    public void givenQueryWhenFilteringRecordsByQueryThenAverageWaitingTime() {
        int avgTime = recordService.getAverageWaitingTimeByQuery(replies, queryText);
        assertEquals(resultAverageTime, avgTime);
    }

    @Test
    public void shouldCallGetAverageWaitingTimeByQueryMethod() {
        String[] queries = {
                "D 1.1 8 P 01.01.2012-01.12.2012",
                "D 1.1 8 P 01.01.2012-01.12.2012",
                "D 1.1 8 P 01.01.2012-01.12.2012",
                "D 1.1 8 P 01.01.2012-01.12.2012",
                "D 1.1 8 P 01.01.2012-01.12.2012"
        };
        RecordServiceImpl recordService = spy(new RecordServiceImpl());
        when(recordService.getAverageWaitingTimeByQuery(replies, "D 1.1 8 P 01.01.2012-01.12.2012")).thenReturn(83);
        recordService.getAverageWaitingTimeByQueries(replies, Arrays.asList(queries));
        verify(recordService, times(queries.length)).getAverageWaitingTimeByQuery(replies, "D 1.1 8 P 01.01.2012-01.12.2012");
    }
}
