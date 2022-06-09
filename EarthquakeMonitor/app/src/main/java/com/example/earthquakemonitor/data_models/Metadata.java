package com.example.earthquakemonitor.data_models;

public class Metadata {
    Long generated;
    Integer count;

   public Metadata(Long generated, Integer count) {
    this.generated = generated;
    this.count = count;
   }

   public Long getGenerated() {
    return generated;
   }

   public void setGenerated(Long generated) {
    this.generated = generated;
   }

   public Integer getCount() {
    return count;
   }

   public void setCount(Integer count) {
    this.count = count;
   }

 @Override
 public String toString() {
  return "Metadata{" +
          "generated=" + generated +
          ", count=" + count +
          '}';
 }
}
