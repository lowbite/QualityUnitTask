package com.qualityunit.task;

import com.qualityunit.task.constants.ResponseType;
import com.qualityunit.task.model.Question;
import com.qualityunit.task.model.Reply;
import com.qualityunit.task.model.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class ReplyTest {
    @Parameter(0)
    public String replyText;
    @Parameter(1)
    public Reply resultReply;

    @Parameters
    public static Collection<Object[]> queryParameters() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Reply result1 = new Reply.Builder().setService(new Service(1, 1))
                .setQuestion(new Question(8, 15, 1))
                .setResponseType(ResponseType.P)
                .setDate(formatter.parse("15.10.2012").getTime())
                .setWaitingTime(83)
                .build();
        Reply result2 = new Reply.Builder().setService(new Service(1, 0))
                .setQuestion(new Question(10, 1, 0))
                .setResponseType(ResponseType.P)
                .setDate(formatter.parse("01.12.2012").getTime())
                .setWaitingTime(65)
                .build();
        Reply result3 = new Reply.Builder().setService(new Service(1, 1))
                .setQuestion(new Question(5, 5, 1))
                .setResponseType(ResponseType.P)
                .setDate(formatter.parse("01.11.2012").getTime())
                .setWaitingTime(117)
                .build();
        Reply result4 = new Reply.Builder().setService(new Service(3, 0))
                .setQuestion(new Question(10, 2, 0))
                .setResponseType(ResponseType.N)
                .setDate(formatter.parse("02.10.2012").getTime())
                .setWaitingTime(100)
                .build();
        Object[][] params = {
                {"C 1.1 8.15.1 P 15.10.2012 83", result1},
                {"C 1 10.1 P 01.12.2012 65", result2},
                {"C 1.1 5.5.1 P 01.11.2012 117", result3},
                {"C 3 10.2 N 02.10.2012 100", result4}
        };
        return Arrays.asList(params);
    }

    @Test
    public void convertStringToReply() {
        Reply reply = Reply.fromString(replyText);
        assertEquals(resultReply, reply);
    }
}
