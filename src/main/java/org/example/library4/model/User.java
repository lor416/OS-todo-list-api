package org.example.library4.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    private String username;
    private String password;
    private String role;

    @XmlElementWrapper(name = "borrowedBooks")
    @XmlElement(name = "bookId")
    private List<String> borrowedBooks = new ArrayList<>();
}

