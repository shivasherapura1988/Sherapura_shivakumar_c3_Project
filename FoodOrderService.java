public class FoodOrderService {

    private final ItemRepository itemRepository;
    private Order order;

    public FoodOrderService() {
        itemRepository = new BasicItemRepository();
    }

    public Optional<Order> getOrder() {
        return Optional.ofNullable(order);
    }

    public void addItem(Item item) {
        if(order == null) {
            order = new Order();
        }
        order.items.add(item);

        BigDecimal itemPrice = itemRepository.getItemPrice(item);
        order.price = order.price.add(itemPrice);
    }

    public void removeItem(Item item) {
        getOrder().ifPresent(order -> {
            order.items.remove(item);
            order.price = order.price.subtract(itemRepository.getItemPrice(item));
        });
    }
}

interface ItemRepository {
    BigDecimal getItemPrice(Item item);
}

public class BasicItemRepository implements ItemRepository {

    @Override
    public BigDecimal getItemPrice(Item item) {
        if(item.name.equalsIgnoreCase("Chicken Sandwich")) {
            return new BigDecimal(9);
        } else if(item.name.equalsIgnoreCase("Oreo Cheesecake")) {
            return new BigDecimal(7);
        } else if(item.name.equalsIgnoreCase("Cheeseburger")) {
            return new BigDecimal(9);
        }
        throw new IllegalArgumentException("Unknown item " + item.name);
    }
}