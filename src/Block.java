import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private int blockSize; // 이 필드를 제외한 나머지 데이터들의 크기 (단위 : Byte)
    private BlockHeader blockHeader; // 블록의 메타 데이터를 담고 있는 객체
    private int transcationCount; // 거래의 수를 저장하는 필드
    private Object[] transactions; // 거래 정보를 담고 있는 컬렉션


    Block(BlockHeader blockHeader, Object[] transactions) {
        this.blockHeader = blockHeader;
        this.transactions = transactions;
    }

    BlockHeader getBlockHeader() {
        return blockHeader;
    }

    String getTransactions() {
        String str = "";
        for (int i = 0; i < transactions.length; i++) {
            str += transactions[i] + "\n";
        }
        return str;
    }

    /**
     * 블록 해시값은 블록 내에서 데이터 형태로 저장되어있지 않고 필요한 시점에 네트워크를 구성하는 노드에서 계산됨
     *
     * @return blockHash
     */
    String getBlockHash() {
        String hash;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(blockHeader.toByteArray());
            byte[] blockHash = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < blockHash.length; i++) {
                sb.append(Integer.toString((blockHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException nse) {
            nse.printStackTrace();
            hash = null;
        }
        return hash;
    }
}
