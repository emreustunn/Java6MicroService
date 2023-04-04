package com.emre.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "userprofile") //tablonun adı
public class UserProfile extends BaseEntity{
    @Id
    String id; // burada id string tutulur. uuid şeklinde tutuldukları için

    Long userprofileid; //UserProfile sınıf içindeki id'dir.
    Long authid;
    String username;
    String email;
    String ad;
    String adres;
    String telefon;
    String avatar;

}
