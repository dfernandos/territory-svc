package com.mapeando.territory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "territory")
public class Territory {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "content1")
    private String content1;

    @Field(name = "content2")
    private String content2;

    @Field(name = "content3")
    private String content3;

    @Field(name = "content4")
    private String content4;

}
