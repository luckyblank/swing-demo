package com.wuzf.swing.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RowInfo {
    /****
     * rowNum
     */

   private Integer  rowNum;

   /***
    *
    * cellValue
    * */

   private String cellValue;

}
