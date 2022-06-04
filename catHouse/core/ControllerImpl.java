package catHouse.core;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerImpl implements Controller {
    private final ToyRepository toys;
    private final Map<String, House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new LinkedHashMap<>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house = null;
        switch (type) {
            case "LongHouse":
                house = new LongHouse(name);
                break;
            case "ShortHouse":
                house = new ShortHouse(name);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_HOUSE_TYPE);
        }
        this.houses.put(house.getName(), house);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
        Toy toy = null;
        switch (type) {
            case "Ball":
                toy = new Ball();
                break;
            case "Mouse":
                toy = new Mouse();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_TOY_TYPE);
        }

            this.toys.buyToy(toy);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy toy = this.toys.findFirst(toyType);

        if (toy == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_TOY_FOUND, toyType));
        }
        this.houses.get(houseName).buyToy(toy);
        this.toys.removeToy(toy);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        Cat cat = null;
        switch (catType) {
            case "ShorthairCat":
                cat = new ShorthairCat(catName, catBreed, price);
                break;
            case "LonghairCat":
                cat = new LonghairCat(catName, catBreed, price);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_CAT_TYPE);
        }
        House house = this.houses.get(houseName);
        if (house.getClass().getSimpleName().equals("LongHouse") && cat.getClass().getSimpleName().equals("LonghairCat")) {
            house.addCat(cat);
        } else if (house.getClass().getSimpleName().equals("ShortHouse") && cat.getClass().getSimpleName().equals("ShorthairCat")) {
            house.addCat(cat);
        } else {
            return ConstantMessages.UNSUITABLE_HOUSE;
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
    }

    @Override
    public String feedingCat(String houseName) {
       House house = this.houses.get(houseName);
        house.feeding();

        return String.format(ConstantMessages.FEEDING_CAT,house.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        House house = this.houses.get(houseName);

        double catsPrice = house.getCats().stream().mapToDouble(Cat::getPrice).sum();
        double toysPrice = house.getToys().stream().mapToDouble(Toy::getPrice).sum();

        double total = catsPrice + toysPrice;
        return String.format(ConstantMessages.VALUE_HOUSE,houseName,total);
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        for (House house: houses.values()) {
            builder.append(house.getStatistics()) .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

}
