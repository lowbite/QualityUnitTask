package com.qualityunit.task.model;

import com.qualityunit.task.constants.ResponseType;
import com.qualityunit.task.constants.Regex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query {
    private Service service;
    private Question question;
    private ResponseType responseType;
    private long dateFrom;
    private long dateTo;

    public Query() {
    }

    Query(Service service, Question question, ResponseType responseType, long dateFrom, long dateTo) {
        this.service = service;
        this.question = question;
        this.responseType = responseType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public long getDateTo() {
        return dateTo;
    }

    public void setDateTo(long dateTo) {
        this.dateTo = dateTo;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Query query = (Query) o;

        if (dateFrom != query.dateFrom) return false;
        if (dateTo != query.dateTo) return false;
        if (service != null ? !service.equals(query.service) : query.service != null) return false;
        if (question != null ? !question.equals(query.question) : query.question != null) return false;
        return responseType == query.responseType;
    }

    @Override
    public int hashCode() {
        int result = service != null ? service.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + responseType.hashCode();
        result = 31 * result + (int) (dateFrom ^ (dateFrom >>> 32));
        result = 31 * result + (int) (dateTo ^ (dateTo >>> 32));
        return result;
    }

    public static Query fromString(String queryStr) {
        if (queryStr.matches(Regex.QUERY)) {
            Query query = new Query();
            Pattern pattern;
            Matcher matcher;
            String[] queryParams = queryStr.split("\\s");
            if(!queryParams[1].equals("*")) {
                pattern = Pattern.compile(Regex.QUERY_SERVICE_VERSION);
                matcher = pattern.matcher(queryParams[1]);
                if (matcher.find()) {
                    query.setService(Service.fromString(queryParams[1]));
                }
            }
            if(!queryParams[2].equals("*")) {
                pattern = Pattern.compile(Regex.QUERY_QUESTION_TYPE);
                matcher = pattern.matcher(queryParams[2]);
                if (matcher.find()) {
                    query.setQuestion(Question.fromString(queryParams[2]));
                }
            }
            query.setResponseType(ResponseType.valueOf(queryParams[3]));
            pattern = Pattern.compile(Regex.DATE);
            matcher = pattern.matcher(queryParams[4]);
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                if (matcher.find()) {
                    query.setDateFrom(formatter.parse(matcher.group()).getTime());
                }
                if(matcher.find()) {
                    query.setDateTo(formatter.parse(matcher.group()).getTime());
                }
            } catch (ParseException e) {
                throw new RuntimeException("Incorrect date format");
            }
            return query;
        } else {
            throw new RuntimeException("Incorrect QUERY format");
        }
    }

    public static class QueryBuilder {
        private Service service;
        private Question question;
        private ResponseType responseType;
        private long dateFrom;
        private long dateTo;

        public QueryBuilder setService(Service service) {
            this.service = service;
            return this;
        }

        public QueryBuilder setQuestion(Question question) {
            this.question = question;
            return this;
        }

        public QueryBuilder setResponseType(ResponseType responseType) {
            this.responseType = responseType;
            return this;
        }

        public QueryBuilder setDateFrom(long dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public QueryBuilder setDateTo(long dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public Query build() {
            return new Query(service, question, responseType, dateFrom, dateTo);
        }
    }
}
