package DAO;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.ObjectNotFoundException;

import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;

public class DAO implements AutoCloseable{
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public DAO() {
        String persistanceName = "default";
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistanceName);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public boolean create(Object object) {
        boolean result = true;
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();

            entityManager.persist(object);

            transaction.commit();

        }catch(Exception e) {
            System.err.println(e.getMessage());
        }finally {
            if(transaction.isActive()) {
                System.out.println("rolling back............");
                transaction.rollback();
                result = false;
            }
        }

        return result;
    }


    public Optional<Object> read(Class<?> classObject, Object primaryKey) {
        try {
            if (typeCheck(primaryKey)) {
                Object object = entityManager.find(classObject, primaryKey);
                return Optional.of(object);
            }else{
                System.err.println("IllegalArgumentException : -" + primaryKey.getClass().getName());
            }
        } catch (IllegalArgumentException | ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return Optional.empty();
    }

    public Optional<Object> update(Class<?> classObject, Object primaryKey , Map<String, Object> updateMap) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Object object = entityManager.find(classObject, primaryKey);
            entityManager.clear();
            Map<String, Object> filteredUpdateMap = filterFields(classObject, updateMap);
            Method[] methods = classObject.getDeclaredMethods();
            Map<String, Method> mapOfMethods = new HashMap<>();

            for(Method method : methods) {
//                System.out.println(method.getName());
                mapOfMethods.put(method.getName(), method);
            }

            for(Map.Entry<String, Object> entrySet : filteredUpdateMap.entrySet()) {
                String fieldName = entrySet.getKey();
                Object fieldValue = entrySet.getValue();

                String correspondingSetterFunctionName =
                                        "set" +
                                        fieldName.substring(0, 1).toUpperCase() +
                                        fieldName.substring(1);

                if(mapOfMethods.containsKey(correspondingSetterFunctionName)) {
                    Method method = mapOfMethods.get(correspondingSetterFunctionName);

                    method.setAccessible(true);
                    method.invoke(object, fieldValue);
                }

            }

            entityManager.merge(object);

            transaction.commit();

            return Optional.of(object);
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }finally {
            if(transaction.isActive()) {
                System.out.println("rolling back............");
                transaction.rollback();
            }
        }

        return Optional.empty();
    }

    private Map<String, Object> filterFields(Class<?> classObject, Map<String, Object> map) {

        Map<String, Object> resultMap = new HashMap<>();
        Set<Field> set = new HashSet<>(Arrays.asList(classObject.getDeclaredFields()));

        for(Field field : set) {
            if(
                    map.containsKey(field.getName()) &&
                    field.getType().isInstance(map.get(field.getName()))
            ) {
                field.setAccessible(true);
                resultMap.put(field.getName(), map.get(field.getName()));
            }
        }

        return resultMap;
    }

    public boolean delete(Class<?> classObject, Object primaryKey) {
        EntityTransaction transaction = entityManager.getTransaction();
        boolean result = true;

        if (!typeCheck(primaryKey)){
            System.err.println("IllegalArgumentException : -" + primaryKey.getClass().getName());
            return false;
        }


        try {
            transaction.begin();
            Optional<Object> object = this.read(classObject, primaryKey);
            if(object.isPresent())
                entityManager.remove(object.get());
            else{
                System.out.println("Not found");
            }
            transaction.commit();
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }finally {
            if(transaction.isActive()) {
                System.out.println("rolling back............");
                transaction.rollback();
                result = false;
            }
        }

        return result;
    }

    private boolean typeCheck(Object key) {
        return key instanceof Integer || key instanceof String ||
                key instanceof Long || key instanceof BigInteger;
    }


    @Override
    public void close() throws Exception {
        System.out.println("closing DAO......................");
        entityManager.close();
        entityManagerFactory.close();
    }
}
