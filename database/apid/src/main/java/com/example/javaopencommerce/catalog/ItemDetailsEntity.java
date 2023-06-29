package com.example.javaopencommerce.catalog;


import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ItemDetails")
@Table(name = "item_details")
class ItemDetailsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;

  @OneToMany
  @JoinTable(name = "item_details_image", joinColumns = {
      @JoinColumn(name = "item_details_id")}, inverseJoinColumns = {@JoinColumn(name = "image_id")})
  private List<ImageEntity> additionalImages;
}
