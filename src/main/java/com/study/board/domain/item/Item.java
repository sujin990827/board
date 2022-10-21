package com.study.board.domain.item;
import com.study.board.domain.Category;
import com.study.board.exception.NotEnoughStockException;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //== 비즈니스 로직 ==//
    //엔티티 안에 비즈니스 로직을 넣는게 좋다. 가장 응집도가 높다
    //재고 수량 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    //재고 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity; //나의 수량 - 넘어온 수량
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
