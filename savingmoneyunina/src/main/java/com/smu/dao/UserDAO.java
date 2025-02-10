package com.smu.dao;

import java.util.List;
import com.smu.model.Family;
import com.smu.model.User;
import com.smu.model.PaymentCard;
import com.smu.model.Category;

public interface UserDAO extends DAO<User, String>
{
    Boolean checkValidUser(String username, String password);

    List<PaymentCard> getCards(User user);

    List<Category> getCategories(User user);

    Category getOtherCategory(User user);

    Family getFamily(User user);
}