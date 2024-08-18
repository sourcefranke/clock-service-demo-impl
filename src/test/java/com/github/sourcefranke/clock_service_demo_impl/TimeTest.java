package com.github.sourcefranke.clock_service_demo_impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sourcefranke.clock_service_demo_impl.model.GetTime200Response;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TimeTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getTime_defaultValues() throws Exception {
		var json = mockMvc.perform(get("/time"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		var result = new ObjectMapper().readValue(json, GetTime200Response.class);

		assertThat(result)
				.usingRecursiveComparison()
				.ignoringExpectedNullFields()
				.isEqualTo(
						new GetTime200Response()
								.dateFormat("yyyy-MM-dd'T'HH:mm:ss")
								.timeZone("GMT")
				);

		assertThat(result.getTime()).matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");
	}

	@ParameterizedTest
	@CsvSource({
			"HH:mm:ss, ^\\d{2}:\\d{2}:\\d{2}$"
	})
	void getTime_dateFormatSet(String dateFormat, String regex) throws Exception {
		var json = mockMvc.perform(
						get("/time")
								.queryParam("date-format", dateFormat)
				)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		var result = new ObjectMapper().readValue(json, GetTime200Response.class);

		assertThat(result)
				.usingRecursiveComparison()
				.ignoringExpectedNullFields()
				.isEqualTo(
						new GetTime200Response()
								.dateFormat(dateFormat)
								.timeZone("GMT")
				);

		assertThat(result.getTime()).matches(regex);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/timezones.csv")
	void getTime_timeZoneSet(String timeZone) throws Exception {
		var json = mockMvc.perform(
						get("/time")
								.queryParam("time-zone", timeZone)
				)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		var result = new ObjectMapper().readValue(json, GetTime200Response.class);

		assertThat(result)
				.usingRecursiveComparison()
				.ignoringExpectedNullFields()
				.isEqualTo(new GetTime200Response()
						.dateFormat("yyyy-MM-dd'T'HH:mm:ss")
						.timeZone(timeZone));
	}

	@Test
	void getTime_invalidDateFormat() throws Exception {
		try {
			mockMvc.perform(get("/time")
					.queryParam("date-format", "HH:mm:ssssssss")
			);
			fail("Call should have failed!");
		}
		catch (ServletException e) {
			assertThat(e.getMessage()).startsWith("Request processing failed");
		}
	}

	@Test
	void getTime_invalidTimeZone() throws Exception {
		try {
			mockMvc.perform(get("/time")
					.queryParam("time-zone", "CEST")
			);
			fail("Call should have failed!");
		}
		catch (ServletException e) {
			assertThat(e.getMessage()).startsWith("Request processing failed");
		}
	}
}
