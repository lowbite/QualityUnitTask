package com.qualityunit.task;

import com.qualityunit.task.constants.ResponseType;
import com.qualityunit.task.model.Query;
import com.qualityunit.task.model.Question;
import com.qualityunit.task.model.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class QueryTest {
    @Parameter(0)
    public String queryText;
    @Parameter(1)
    public Query resultQuery;

    @Parameters
    public static Collection<Object[]> queryParameters() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Query result1 = new Query.QueryBuilder().setService(new Service(1, 1))
                .setQuestion(new Question(8, 0, 0))
                .setResponseType(ResponseType.P)
                .setDateFrom(formatter.parse("01.01.2012").getTime())
                .setDateTo(formatter.parse("01.12.2012").getTime())
                .build();
        Query result2 = new Query.QueryBuilder().setService(new Service(1, 0))
                .setResponseType(ResponseType.P)
                .setDateFrom(formatter.parse("08.10.2012").getTime())
                .setDateTo(formatter.parse("20.11.2012").getTime())
                .build();
        Query result3 = new Query.QueryBuilder().setService(new Service(3, 0))
                .setQuestion(new Question(10, 0, 0))
                .setResponseType(ResponseType.P)
                .setDateFrom(formatter.parse("01.12.2012").getTime())
                .build();
        Object[][] params = {
                {"D 1.1 8 P 01.01.2012-01.12.2012", result1},
                {"D 1 * P 8.10.2012-20.11.2012", result2},
                {"D 3 10 P 01.12.2012", result3}
        };
        return Arrays.asList(params);
    }

    @Test
    public void convertStringToQuery() {
        Query query = Query.fromString(queryText);
        assertEquals(resultQuery, query);
    }
}
