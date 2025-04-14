package org.hackit.space.managerapp.client;

import lombok.RequiredArgsConstructor;
import org.hackit.space.managerapp.controller.entity.Hackathon;
import org.hackit.space.managerapp.controller.payload.NewHackathonPayload;
import org.hackit.space.managerapp.controller.payload.UpdateHackathonPayload;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class HackathonsRestClientImpl implements HackathonsRestClient {

    private static final ParameterizedTypeReference<List<Hackathon>> HACKATHONS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Hackathon> findAllHackathons() {
        return this.restClient
                .get()
                .uri("/hackathons-api/hackathons")
                .retrieve()
                .body(HACKATHONS_TYPE_REFERENCE);
    }

    @Override
    public Hackathon createHackathon(String title, String description) {
        try {
            return this.restClient
                    .post()
                    .uri("/hackathons-api/hackathons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewHackathonPayload(title, description))
                    .retrieve()
                    .body(Hackathon.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Hackathon> findHackathon(int hackathonId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/hackathons-api/hackathons/{hackathonId}", hackathonId)
                    .retrieve()
                    .body(Hackathon.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateHackathon(int hackathonId, String title, String description) {
        try {
            this.restClient
                    .patch()
                    .uri("/hackathons-api/hackathons/{hackathonId}", hackathonId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateHackathonPayload(title, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteHackathon(int hackathonId) {
        try {
            this.restClient
                    .delete()
                    .uri("/hackathons-api/hackathons/{hackathonId}", hackathonId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
