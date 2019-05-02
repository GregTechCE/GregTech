//package test;
//
//import gregtech.api.util.BlockPosQueue;
//import net.minecraft.util.math.BlockPos;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TestBlockPosQueue {
//    private BlockPosQueue queue = new BlockPosQueue();
//
//    @Test
//    public void testRandomInsert() {
//        BlockPos pos = new BlockPos(0, 0, 0);
//        queue.setCenter(pos);
//        List<BlockPos> list = new ArrayList<>(2048);
//        Random random = new Random();
//
//        for (int i = 0; i < 2048; i ++) {
//            pos = new BlockPos(random.nextInt() % 50, random.nextInt() % 50, random.nextInt() % 50);
//            list.add(pos);
//            queue.add(pos);
//        }
//
//        for (int i = 0; i < 2048; i ++) {
//            pos = queue.poll();
//            System.out.println("queue: " + pos.getX() + "   list: " + list.get(i).getX());
//            System.out.println("queue: " + pos.getY() + "   list: " + list.get(i).getY());
//            System.out.println("queue: " + pos.getZ() + "   list: " + list.get(i).getZ());
//            assertTrue(pos.equals(list.get(i)));
//        }
//    }
//}
