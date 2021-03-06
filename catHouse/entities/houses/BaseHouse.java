package catHouse.entities.houses;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseHouse implements House{
    private String name;
    private int capacity;
    private Collection<Toy> toys;
    private Collection<Cat> cats;

    public BaseHouse(String name, int capacity) {
        this.setName(name);
        this.setCapacity(capacity);
        this.toys = new ArrayList<>();
        this.cats = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (name==null||name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }else {
            this.name = name;
        }
    }

    public int getCapacity() {
        return this.capacity;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Collection<Toy> getToys() {
        return this.toys;
    }


    @Override
    public Collection<Cat> getCats() {
        return this.cats;
    }

    @Override
    public int sumSoftness() {
        return this.toys
                .stream()
                .mapToInt(Toy::getSoftness)
                .sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (this.cats.size()<this.capacity) {
            this.cats.add(cat);
        }
    }

    @Override
    public void removeCat(Cat cat) {
        this.cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        this.toys.add(toy);
    }

    @Override
    public void feeding() {
        this.cats.forEach(Cat::eating);
    }

    @Override
    public String getStatistics() {
        StringBuilder stats = new StringBuilder();

        stats.append(String.format("%s %s:", this.name,
                this.getClass().getSimpleName()))
                .append(System.lineSeparator());

        if (cats.size() == 0) {
            stats.append("Cats: none").append(System.lineSeparator());
        } else {
            stats.append("Cats:");

            for (Cat cat : cats) {
                stats.append(" ").append(cat.getName());
            }

            stats.append(System.lineSeparator());
        }

        int toysCount = toys.size();
        int sumOfSoftness = toys.stream().mapToInt(Toy::getSoftness).sum();

        stats.append(String.format("Toys: %d Softness: %d", toysCount, sumOfSoftness))
                .append(System.lineSeparator());

        return stats.toString().trim();
    }
}
