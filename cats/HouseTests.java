package cats;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HouseTests {
    private Cat firstCat;
    private Cat secondCat;
    private House house;

    @Before
    public void setUp(){
        firstCat = new Cat("Jesy");
        secondCat = new Cat("Shishko");
        house = new House("CatHouse",2);
        house.addCat(firstCat);
    }
    @Test
    public void testShouldReturnHousesNameCorrectly(){
        String returned = this.house.getName();
        Assert.assertEquals("CatHouse",returned);
    }
    @Test
    public void testShouldReturnHousesCapacityCorrectly(){
        int returned = this.house.getCapacity();
        Assert.assertEquals(2,returned);
    }
    @Test
    public void testShouldReturnCatsCountCorrectly(){
        int returned = this.house.getCount();
        Assert.assertEquals(1,returned);
    }
    @Test
    public void testShouldAddCatCorrectly(){
        this.house.addCat(secondCat);
        int returned = this.house.getCount();
        Assert.assertEquals(2,returned);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionWhenHouseIsFull(){
        this.house.addCat(secondCat);
        Cat cat = new Cat("Topcho");
        this.house.addCat(cat);
    }
    @Test
    public void testShouldRemoveCatCorrectly(){
        this.house.addCat(secondCat);
        this.house.removeCat("Shishko");
        int returned = this.house.getCount();
        Assert.assertEquals(1,returned);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionWhenCatIsNotHere(){
        this.house.removeCat("Topcho");
    }
    @Test
    public void testShouldShowStatisticsCorrectly(){
        this.house.addCat(secondCat);
       String expected = "The cat Jesy, Shishko is in the house CatHouse!";
       String returned = this.house.statistics();
       Assert.assertEquals(expected,returned);
    }
    @Test
    public void testShouldReturnCatCorrectly(){
        this.house.addCat(secondCat);
        Cat returned = this.house.catForSale("Shishko");
        Assert.assertEquals(secondCat,returned);
    }
    @Test
    public void testShouldSetCatToNotHungryCorrectly(){
        this.house.addCat(secondCat);
        Cat returned = this.house.catForSale("Shishko");
        boolean isHungry = returned.isHungry();
        Assert.assertFalse(isHungry);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionWhenCatIsAbsent(){
       this.house.catForSale("Shishko");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionWhenCapacityIsIncorrect(){
        this.house = new House("Home",-2);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldThrowExceptionWhenNAmeIsIncorrect(){
        this.house = new House(" ",5);
    }

}
