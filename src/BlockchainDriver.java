import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class BlockchainDriver {

    private List<Block> blockchain = new ArrayList<>();

    /**
     * 거래 내역(transaction)이 위변조 되면 블록 해시의 값이 변경되어
     * 연결된 블록의 previousHash값과 일치하지 않아 블록체인의 견고함이 유지
     *
     * @param args
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        new BlockchainDriver().previousBlockExample();
    }

    /**
     * 최초 생성 후 거래내역 변조 시 해시가 바뀌는 예제
     */
    private void forgeryExample() {
        // genesis block (최초 블록)
        String[] transcations = {"mos sent 1k Bitcoins to dragon"};
        Block genesisBlock = new Block(new BlockHeader(null, transcations), transcations);
        System.out.println("Block Hash : " + genesisBlock.getBlockHash());

        // 위변조
        transcations[0] = "mos sent 10k Bitcoins to dragon";
        genesisBlock = new Block(new BlockHeader(null, transcations), transcations);
        System.out.println("Block Hash : " + genesisBlock.getBlockHash());
    }

    private void previousBlockExample() {
        // genesis block (최초 블록)
        String[] transactions = {"mos sent 1k bitcoins to dragon"};
        Block genesisBlock = new Block(new BlockHeader(null, transactions), transactions);
        System.out.println("First Block Hash : \t\t" + genesisBlock.getBlockHash());
        blockchain.add(genesisBlock);

        // second block (두 번째 블록)
        String[] secondTransactions = {"dragon sent 5k bitcoins to mos"};
        Block secondBlock = new Block(new BlockHeader(genesisBlock.getBlockHash().getBytes(), secondTransactions), secondTransactions);
        System.out.println("Second Block Hash : \t" + secondBlock.getBlockHash());
        blockchain.add(secondBlock);

        // third block (세 번째 블록)
        String[] thirdTransactions = {"mos sent 500 bitcoins to dragon"};
        Block thirdBlock = new Block(new BlockHeader(secondBlock.getBlockHash().getBytes(), thirdTransactions), thirdTransactions);
        System.out.println("Third Block Hash : \t\t" + thirdBlock.getBlockHash());
        blockchain.add(thirdBlock);

        System.out.println("------------------------------------------");
        System.out.println("초기 블록");
        showTransactions(genesisBlock);
        System.out.println("------------------------------------------");
        System.out.println("두 번째 블록");
        showTransactions(secondBlock);
        System.out.println("------------------------------------------");
        System.out.println("세 번째 블록");
        showTransactions(thirdBlock);
    }

    /**
     * 저장된 블록헤더의 바이트값을 해시로 디코드
     * @param previousBlockHash
     * @return
     */
    private String decodeBlockHash(byte[] previousBlockHash) {
        if (previousBlockHash == null) {
            return null;
        }
        StringBuilder s = new StringBuilder();
        for (byte b : previousBlockHash) {
            s.append((char) b);
        }
        return s.toString();
    }

    /**
     * 거래 내역을 보여줍니다.
     * 시간은 최신순으로 마치 스택처럼 구현됨 (블록이 늦게 생성된 순서)
     * @param block
     */
    private void showTransactions(Block block) {
        while (true) {
            System.out.println(block.getTransactions().trim());
            if(block.getBlockHeader().getPreviousBlockHash() == null) {
                break;
            }
            for(Block b : blockchain) {
                if(b.getBlockHash().equals(decodeBlockHash(block.getBlockHeader().getPreviousBlockHash()))) {
                    block = b;
                    break;
                }
            }
        }
    }
}
