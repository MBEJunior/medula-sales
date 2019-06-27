package com.medulasales.products;

import com.medulasales.products.utils.uniql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FastConfirmationTest Should All Succed")
public class FastConfirmationTest {

    private static Logger logger = LogManager.getLogger(FastConfirmationTest.class);

    @Test
    @DisplayName("Should Succed")
    void ShouldSucced() {
        Uniql uniql = Uniql.build("category")
          .addField("name")
          .addField("description")
          .addField(Uniql.build("subCategories")
            .addField("name")
            .addField("description")
          )
          .addField(Uniql.build("products")
            .addField("name")
            .addField("description")
            .setPage(PageRequest.of(13, 100))
            .setSort(SortRequest.of(Direction.ASC, new String[] {"name", "unitPrice"}))
          )
          .setQuery("setName=!=skdm;description==samsu%");

        String model = uniql.toModel();
        String fModel = uniql.toFormattedModel();

        System.out.println(model);
        System.out.println(fModel);

        try {
            Uniql parsed = UniqlParser.parse(model);
            System.out.println(parsed.toFormattedModel());
        }catch (UniqlParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("Should Succed")
    void ShouldSucced1() {
        try {
            Uniql parsed = UniqlParser.parse("product{setName, description, category{setName, tag{setName}|search||}}");
            System.out.println(parsed.toFormattedModel());
        }catch (UniqlParseException e) {
            e.printStackTrace();
        }
    }
}
