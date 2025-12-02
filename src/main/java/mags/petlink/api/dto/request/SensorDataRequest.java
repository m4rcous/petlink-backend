package mags.petlink.api.dto.request;

public record SensorDataRequest(
        String device_id,
        String timestamp,
        int bpm
) {
}
