package com.qualityunit.task.model;

import com.qualityunit.task.constants.ResponseType;
import com.qualityunit.task.constants.Regex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reply {
    private Service service;
    private Question question;
    private ResponseType responseType;
    private long date;
    private long waitingTime;

    public Reply() {
    }

    public Reply(Service service, Question question, ResponseType responseType, long date, long waitingTime) {
        this.service = service;
        this.question = question;
        this.responseType = responseType;
        this.date = date;
        this.waitingTime = waitingTime;
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

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reply reply = (Reply) o;

        if (date != reply.date) return false;
        if (waitingTime != reply.waitingTime) return false;
        if (!service.equals(reply.service)) return false;
        if (!question.equals(reply.question)) return false;
        return responseType == reply.responseType;
    }

    @Override
    public int hashCode() {
        int result = service.hashCode();
        result = 31 * result + question.hashCode();
        result = 31 * result + responseType.hashCode();
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (int) (waitingTime ^ (waitingTime >>> 32));
        return result;
    }

    public static Reply fromString(String replyStr) {
        if (replyStr.matches(Regex.REPLY)) {
            Reply reply = new Reply();
            Pattern pattern;
            Matcher matcher;
            String[] queryParams = replyStr.split("\\s");
            pattern = Pattern.compile(Regex.QUERY_SERVICE_VERSION);
            matcher = pattern.matcher(queryParams[1]);
            if (matcher.find()) {
                reply.setService(Service.fromString(queryParams[1]));
            }
            pattern = Pattern.compile(Regex.QUERY_QUESTION_TYPE);
            matcher = pattern.matcher(queryParams[2]);
            if (matcher.find()) {
                reply.setQuestion(Question.fromString(queryParams[2]));
            }
            reply.setResponseType(ResponseType.valueOf(queryParams[3]));
            pattern = Pattern.compile(Regex.DATE);
            matcher = pattern.matcher(queryParams[4]);
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                if (matcher.find()) {
                    reply.setDate(formatter.parse(matcher.group()).getTime());
                }
            } catch (ParseException e) {
                throw new RuntimeException("Incorrect date format");
            }
            reply.setWaitingTime(Long.valueOf(queryParams[5]));
            return reply;
        } else {
            throw new RuntimeException("Incorrect QUERY format");
        }
    }

    public static class Builder {
        private Service service;
        private Question question;
        private ResponseType responseType;
        private long date;
        private long waitingTime;

        public Builder setService(Service service) {
            this.service = service;
            return this;
        }

        public Builder setQuestion(Question question) {
            this.question = question;
            return this;
        }

        public Builder setResponseType(ResponseType responseType) {
            this.responseType = responseType;
            return this;
        }

        public Builder setDate(long date) {
            this.date = date;
            return this;
        }

        public Builder setWaitingTime(long waitingTime) {
            this.waitingTime = waitingTime;
            return this;
        }

        public Reply build() {
            return new Reply(service, question, responseType, date, waitingTime);
        }
    }
}
