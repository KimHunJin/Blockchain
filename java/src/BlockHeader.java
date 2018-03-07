import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class BlockHeader {
    private int version; // 소프트웨어 또는 프로토콜 등의 업그레이트를 추적하기 위해 사용되는 버전 정보
    private byte[] previousBlockHash; // 블록체인 상의 이전 블록(부모 블록)의 해시값
    private int merkleRootHash; // 머클트리의 루트에 대한 해시값
    private int timestamp; // 해당 블록의 생성 시간
    private int difficultyTarget;  // 채굴과정에서 필요한 작업 증명(Proof of Work) 알고리즘의 난이도 목표
    private int nonce;  // 채굴과정의 작업 증명에서 사용되는 카운터

    byte[] getPreviousBlockHash() {
        return previousBlockHash;
    }

    BlockHeader(byte[] previousBlockHash, Object[] transctions) {
        this.previousBlockHash = previousBlockHash;
        this.merkleRootHash = this.someMethod(transctions);
    }

    byte[] toByteArray() {
        String tmpStr = "";
        if (previousBlockHash != null) {
            tmpStr += new String(previousBlockHash, 0, previousBlockHash.length);
        }
        tmpStr += merkleRootHash;
        return tmpStr.getBytes(StandardCharsets.UTF_8);
    }

    private int someMethod(Object[] transactions) {
        return Arrays.hashCode(transactions);
    }
}
