
import com.fasterxml.jackson.core.type.TypeReference;
import com.makesystem.pidgey.json.JsonConverter;
import com.makesystem.pidgey.tester.AbstractTester;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Richeli.vargas
 */
public class JsonConverter_Tester extends AbstractTester {

    private static final Integer SIZE = 1000;

    public static void main(String[] args) {
        new JsonConverter_Tester().run();
    }
    
    private Integer[] primitiveArray;
    private ObjectOne[] simpleObjectArray;
    private ObjectTwo[] complexObjectArray;
    
    private final Collection<Integer> primitiveCollection = new LinkedList<>();
    private final Collection<ObjectOne> simpleObjectCollection = new LinkedList<>();
    private final Collection<ObjectTwo> complexObjectCollection = new LinkedList<>();

    private final Map<Integer, Integer> primitiveMap = new LinkedHashMap<>();
    private final Map<Integer, ObjectOne> simpleObjectMap = new LinkedHashMap<>();
    private final Map<Integer, ObjectTwo> complexObjectMap = new LinkedHashMap<>();

    private String jsonPrimitiveObject;
    private String jsonSimpleObject;
    private String jsonComplexObject;

    private String jsonPrimitiveObjectArray;
    private String jsonSimpleObjectArray;
    private String jsonComplexObjectArray;
    
    private String jsonPrimitiveObjectCollection;
    private String jsonSimpleObjectCollection;
    private String jsonComplexObjectCollection;

    private String jsonPrimitiveObjectMap;
    private String jsonSimpleObjectMap;
    private String jsonComplexObjectMap;

    @Override
    protected void preExecution() {

        for (int index = 0; index < SIZE; index++) {
            final ObjectOne objectOne = new ObjectOne("Object One " + index, index);
            final ObjectTwo objectTwo = new ObjectTwo(objectOne, "Object Two " + index);

            primitiveCollection.add(index);
            simpleObjectCollection.add(objectOne);
            complexObjectCollection.add(objectTwo);

            primitiveMap.put(index, index);
            simpleObjectMap.put(index, objectOne);
            complexObjectMap.put(index, objectTwo);
        }

        primitiveArray = primitiveCollection.stream().toArray(Integer[]::new);
        simpleObjectArray = simpleObjectCollection.stream().toArray(ObjectOne[]::new);
        complexObjectArray = complexObjectCollection.stream().toArray(ObjectTwo[]::new);
    }

    @Override
    protected void execution() {
        test_write();
        test_read_withTypeReference();
        test_read_withClass();
        test_readArray();
        test_readList();
        test_readSet();
        test_readMap();
    }

    @Override
    protected void posExecution() {
    }

    protected void test_write() {

        Assert(() -> {
            jsonPrimitiveObject = JsonConverter.write(primitiveCollection.iterator().next());
            return true;
        }, true);

        Assert(() -> {
            jsonSimpleObject = JsonConverter.write(simpleObjectCollection.iterator().next());
            return true;
        }, true);

        Assert(() -> {
            jsonComplexObject = JsonConverter.write(complexObjectCollection.iterator().next());
            return true;
        }, true);

        Assert(() -> {
            jsonPrimitiveObjectArray = JsonConverter.write(primitiveArray);
            return true;
        }, true);

        Assert(() -> {
            jsonSimpleObjectArray = JsonConverter.write(simpleObjectArray);
            return true;
        }, true);

        Assert(() -> {
            jsonComplexObjectArray = JsonConverter.write(complexObjectArray);
            return true;
        }, true);
        
        Assert(() -> {
            jsonPrimitiveObjectCollection = JsonConverter.write(primitiveCollection);
            return true;
        }, true);

        Assert(() -> {
            jsonSimpleObjectCollection = JsonConverter.write(simpleObjectCollection);
            return true;
        }, true);

        Assert(() -> {
            jsonComplexObjectCollection = JsonConverter.write(complexObjectCollection);
            return true;
        }, true);

        Assert(() -> {
            jsonPrimitiveObjectMap = JsonConverter.write(primitiveMap);
            return true;
        }, true);

        Assert(() -> {
            jsonSimpleObjectMap = JsonConverter.write(simpleObjectMap);
            return true;
        }, true);

        Assert(() -> {
            jsonComplexObjectMap = JsonConverter.write(complexObjectMap);
            return true;
        }, true);

    }

    protected void test_read_withTypeReference() {

        Assert(() -> JsonConverter.read(jsonPrimitiveObject, new TypeReference<Integer>() {
        }), primitiveCollection.iterator().next());
        Assert(() -> JsonConverter.read(jsonSimpleObject, new TypeReference<ObjectOne>() {
        }), simpleObjectCollection.iterator().next());
        Assert(() -> JsonConverter.read(jsonComplexObject, new TypeReference<ObjectTwo>() {
        }), complexObjectCollection.iterator().next());

        Assert(() -> JsonConverter.read(jsonPrimitiveObjectArray, new TypeReference<Integer[]>() {
        }), primitiveArray);
        Assert(() -> JsonConverter.read(jsonSimpleObjectArray, new TypeReference<ObjectOne[]>() {
        }), simpleObjectArray);
        Assert(() -> JsonConverter.read(jsonComplexObjectArray, new TypeReference<ObjectTwo[]>() {
        }), complexObjectArray);
        
        Assert(() -> JsonConverter.read(jsonPrimitiveObjectCollection, new TypeReference<LinkedList<Integer>>() {
        }), primitiveCollection);
        Assert(() -> JsonConverter.read(jsonSimpleObjectCollection, new TypeReference<LinkedList<ObjectOne>>() {
        }), simpleObjectCollection);
        Assert(() -> JsonConverter.read(jsonComplexObjectCollection, new TypeReference<LinkedList<ObjectTwo>>() {
        }), complexObjectCollection);

        Assert(() -> JsonConverter.read(jsonPrimitiveObjectMap, new TypeReference<Map<Integer, Integer>>() {
        }), primitiveMap);
        Assert(() -> JsonConverter.read(jsonSimpleObjectMap, new TypeReference<Map<Integer, ObjectOne>>() {
        }), simpleObjectMap);
        Assert(() -> JsonConverter.read(jsonComplexObjectMap, new TypeReference<Map<Integer, ObjectTwo>>() {
        }), complexObjectMap);

    }
    
    protected void test_read_withClass() {
        Assert(() -> JsonConverter.read(jsonPrimitiveObject, Integer.class), primitiveCollection.iterator().next());
        Assert(() -> JsonConverter.read(jsonSimpleObject, ObjectOne.class), simpleObjectCollection.iterator().next());
        Assert(() -> JsonConverter.read(jsonComplexObject, ObjectTwo.class), complexObjectCollection.iterator().next());
    }
    
    protected void test_readArray() {
        Assert(() -> JsonConverter.readArray(jsonPrimitiveObjectArray, Integer.class), primitiveArray);
        Assert(() -> JsonConverter.readArray(jsonSimpleObjectArray, ObjectOne.class), simpleObjectArray);
        Assert(() -> JsonConverter.readArray(jsonComplexObjectArray, ObjectTwo.class), complexObjectArray);
    }
    
    protected void test_readList() {
        Assert(() -> JsonConverter.readList(jsonPrimitiveObjectCollection, Integer.class), primitiveCollection);
        Assert(() -> JsonConverter.readList(jsonSimpleObjectCollection, ObjectOne.class), simpleObjectCollection);
        Assert(() -> JsonConverter.readList(jsonComplexObjectCollection, ObjectTwo.class), complexObjectCollection);
    }
    
    protected void test_readSet() {
        Assert(() -> JsonConverter.readSet(jsonPrimitiveObjectCollection, Integer.class), primitiveCollection);
        Assert(() -> JsonConverter.readSet(jsonSimpleObjectCollection, ObjectOne.class), simpleObjectCollection);
        Assert(() -> JsonConverter.readSet(jsonComplexObjectCollection, ObjectTwo.class), complexObjectCollection);
    }
    
    protected void test_readMap() {
        Assert(() -> JsonConverter.readMap(jsonPrimitiveObjectMap, Integer.class, Integer.class), primitiveMap);
        Assert(() -> JsonConverter.readMap(jsonSimpleObjectMap, Integer.class, ObjectOne.class), simpleObjectMap);
        Assert(() -> JsonConverter.readMap(jsonComplexObjectMap, Integer.class, ObjectTwo.class), complexObjectMap);
    }
    
    // /////////////////////////////////////////////////////////////////////////
    // For tests
    // /////////////////////////////////////////////////////////////////////////
    
    protected static class ObjectOne implements Serializable {

        private String attrOne;
        private int attrTwo;

        public ObjectOne() {
        }

        public ObjectOne(String attrOne, int attrTwo) {
            this.attrOne = attrOne;
            this.attrTwo = attrTwo;
        }

        public String getAttrOne() {
            return attrOne;
        }

        public void setAttrOne(String attrOne) {
            this.attrOne = attrOne;
        }

        public int getAttrTwo() {
            return attrTwo;
        }

        public void setAttrTwo(int attrTwo) {
            this.attrTwo = attrTwo;
        }

        @Override
        public String toString() {
            return "ObjectOne{" + "attrOne=" + attrOne + ", attrTwo=" + attrTwo + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + Objects.hashCode(this.attrOne);
            hash = 37 * hash + this.attrTwo;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ObjectOne other = (ObjectOne) obj;
            if (this.attrTwo != other.attrTwo) {
                return false;
            }
            if (!Objects.equals(this.attrOne, other.attrOne)) {
                return false;
            }
            return true;
        }

    }

    protected static class ObjectTwo implements Serializable {

        private ObjectOne objectOne;
        private String attrOne;

        public ObjectTwo() {
        }

        public ObjectTwo(ObjectOne objectOne, String attrOne) {
            this.objectOne = objectOne;
            this.attrOne = attrOne;
        }

        public ObjectOne getObjectOne() {
            return objectOne;
        }

        public void setObjectOne(ObjectOne objectOne) {
            this.objectOne = objectOne;
        }

        public String getAttrOne() {
            return attrOne;
        }

        public void setAttrOne(String attrOne) {
            this.attrOne = attrOne;
        }

        @Override
        public String toString() {
            return "ObjectTwo{" + "objectOne=" + objectOne + ", attrOne=" + attrOne + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + Objects.hashCode(this.objectOne);
            hash = 41 * hash + Objects.hashCode(this.attrOne);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ObjectTwo other = (ObjectTwo) obj;
            if (!Objects.equals(this.attrOne, other.attrOne)) {
                return false;
            }
            if (!Objects.equals(this.objectOne, other.objectOne)) {
                return false;
            }
            return true;
        }
    }

}
