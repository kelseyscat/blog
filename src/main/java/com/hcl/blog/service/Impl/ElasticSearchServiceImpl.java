package com.hcl.blog.service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.JsonpMapper;
import com.hcl.blog.config.ElasticSearchConfig;
import com.hcl.blog.service.ElasticSearchService;
import jakarta.json.stream.JsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Override
    public void initIndex() {
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
