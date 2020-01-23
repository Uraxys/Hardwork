package no.minecraft.Minecraftno.handlers.data;

public class BlockData {

    private int type;
    private String statment;
    private int playerId;
    private String blockType;
    private int blockX;
    private int blockY;
    private int blockZ;
    private String world;
    private String blocklogreason;

    public BlockData(int type, String Statment, int playerId, String blockType, int blockX, int blockY, int blockZ, String world, String blocklogreason) {
        this.type = type;
        this.statment = Statment;
        this.playerId = playerId;
        this.blockType = blockType;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.world = world;
        this.blocklogreason = blocklogreason;
    }

    public int getType() {
        return this.type;
    }

    public String getStatment() {
        return this.statment;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public String getBlockType() {
        return this.blockType;
    }

    public int getBlockX() {
        return this.blockX;
    }

    public int getBlockY() {
        return this.blockY;
    }

    public int getBlockZ() {
        return this.blockZ;
    }

    public String getWorld() {
        return this.world;
    }

    public String getBlockLogReason() {
        return this.blocklogreason;
    }
}
