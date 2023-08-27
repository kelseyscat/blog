package com.hcl.blog;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.JsonpMapper;
import com.hcl.blog.config.ElasticSearchConfig;
import com.hcl.blog.entity.Article;
import jakarta.json.stream.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {


	@Test
	void contextLoads() throws IOException {
		ElasticSearchConfig cfg = new ElasticSearchConfig();
		ElasticsearchClient client = cfg.getClient();

		String mappingPath = System.getProperty("user.dir") + "/myArticle.json";
		JsonpMapper mapper = client._transport().jsonpMapper();
		String mappings_str = null;
		try {
			mappings_str = new String(Files.readAllBytes(Paths.get(mappingPath)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("mappings are: " +  mappings_str);
		JsonParser parser = mapper.jsonProvider()
				.createParser(new StringReader( mappings_str ));

		try {
			CreateIndexResponse createResponse = client.indices()
					.create(c -> c.index("article")
							.mappings(TypeMapping._DESERIALIZER.deserialize(parser, mapper)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

}
