package com.bressio.rendezvous.graphics;

public enum AnimationRegion {
    PLAYER("player_animation", 0, 1, 64, 6, .1f, 0, 0, 64, 64);
    AnimationRegion(String region, int startRow, int startColumn, int frameSize, int amountFrames, float frameDuration,
                    int idleTextureX, int idleTextureY, int idleTextureWidth, int idleTextureHeight) {
        this.region = region;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.frameSize = frameSize;
        this.amountFrames = amountFrames;
        this.frameDuration = frameDuration;
        this.idleTextureX = idleTextureX;
        this.idleTextureY = idleTextureY;
        this.idleTextureWidth = idleTextureWidth;
        this.idleTextureHeight = idleTextureHeight;
    }

    private String region;
    private int startRow;
    private int startColumn;
    private int frameSize;
    private int amountFrames;
    private float frameDuration;
    private int idleTextureX;
    private int idleTextureY;
    private int idleTextureWidth;
    private int idleTextureHeight;

    public String getRegion() { return region; }
    public int getStartRow() { return startRow; }
    public int getStartColumn() { return startColumn; }
    public int getFrameSize() { return frameSize; }
    public int getAmountFrames() { return amountFrames; }
    public float getFrameDuration() { return frameDuration; }
    public int getIdleTextureX() { return idleTextureX; }
    public int getIdleTextureY() { return idleTextureY; }
    public int getIdleTextureWidth() { return idleTextureWidth; }
    public int getIdleTextureHeight() { return idleTextureHeight; }
}
