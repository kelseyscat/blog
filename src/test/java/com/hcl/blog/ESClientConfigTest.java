package com.hcl.blog;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.hcl.blog.config.ElasticSearchConfig;
import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

public class ESClientConfigTest {
    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {

        ElasticSearchConfig cfg = new ElasticSearchConfig();
        ElasticsearchClient client = cfg.getClient();

        User user = new User();
        user.setId(1);
        user.setUsername("1");
        user.setPassword("1");
        user.setHeadUrl("1");
        user.setCreateTime(new Date());

        CreateResponse createResponse = client.create(e->e.index("user").id("1").document(user));
        System.out.println(createResponse.result());
    }

    /**
     * 查询
     *
     * @throws IOException
     */
    @Test
    public void searchIndex() throws IOException {

        ElasticSearchConfig cfg = new ElasticSearchConfig();
        ElasticsearchClient client = cfg.getClient();

        SearchResponse<User> search = client.search(s -> s
                        .index("user")
                        .query(q -> q
                                .match(t -> t
                                        .field("username")
                                        .query("1")
                                )
                        ),
                User.class);
        System.out.println("0");
    }


}
