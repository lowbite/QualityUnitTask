package com.qualityunit.task.model;

public class Service {
    private int serviceId;
    private int variationId;

    public Service() {
    }

    public Service(int serviceId, int variationId) {
        this.serviceId = serviceId;
        this.variationId = variationId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getVariationId() {
        return variationId;
    }

    public void setVariationId(int variationId) {
        this.variationId = variationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (serviceId != service.serviceId) return false;
        return variationId == service.variationId;
    }

    @Override
    public int hashCode() {
        int result = serviceId;
        result = 31 * result + variationId;
        return result;
    }

    public static Service fromString(String serviceStr){
        Service service = new Service();
        String[] idStrings = serviceStr.split("\\.");
        for (int i = 0; i < idStrings.length; i++) {
            switch (i) {
                case 0:
                    service.setServiceId(Integer.valueOf(idStrings[i]));
                    break;
                case 1:
                    service.setVariationId(Integer.valueOf(idStrings[i]));
                    break;
            }
        }
        return service;
    }
}
