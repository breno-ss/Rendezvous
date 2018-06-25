package com.bressio.rendezvous.graphics;

public enum AnimationRegion {
    SOLDIER("soldier", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_MEDKIT("soldier-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_MILITARY_VEST("soldier-military-vest", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_SOFT_VEST("soldier-soft-vest", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_COMBAT_HELMET("soldier-combat-helmet", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_HALF_HELMET("soldier-half-helmet", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_MV_COMBAT_HELMET("soldier-mv-combat-helmet", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_MV_HALF_HELMET("soldier-mv-half-helmet", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_SV_COMBAT_HELMET("soldier-sv-combat-helmet", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_SV_HALF_HELMET("soldier-sv-half-helmet", 0, 0, 81, 79, 18, .04f, 0, 0, 81, 79),
    SOLDIER_SV_MEDKIT("soldier-sv-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_MV_MEDKIT("soldier-mv-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_CT_MEDKIT("soldier-ct-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_HT_MEDKIT("soldier-ht-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_MV_CT_MEDKIT("soldier-mv-ct-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_MV_HT_MEDKIT("soldier-mv-ht-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_SV_CT_MEDKIT("soldier-sv-ct-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_SV_HT_MEDKIT("soldier-sv-ht-medkit", 0, 0, 78, 77, 18, .04f, 0, 0, 78, 77),
    SOLDIER_STAR("soldier-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_MV_STAR("soldier-mv-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_SV_STAR("soldier-sv-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_CT_STAR("soldier-ct-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_HT_STAR("soldier-ht-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_MV_CT_STAR("soldier-mv-ct-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_MV_HT_STAR("soldier-mv-ht-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_SV_CT_STAR("soldier-sv-ct-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_SV_HT_STAR("soldier-sv-ht-star", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),

    SOLDIER_W16A("soldier-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_MV_W16A("soldier-mv-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_SV_W16A("soldier-sv-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_CT_W16A("soldier-ct-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_HT_W16A("soldier-ht-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_MV_CT_W16A("soldier-mv-ct-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_MV_HT_W16A("soldier-mv-ht-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_SV_CT_W16A("soldier-sv-ct-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105),
    SOLDIER_SV_HT_W16A("soldier-sv-ht-w16a", 0, 0, 57, 105, 18, .04f, 0, 0, 57, 105);
    AnimationRegion(String region, int startRow, int startColumn, int frameWidth, int frameHeight, int amountFrames, float frameDuration,
                    int idleTextureX, int idleTextureY, int idleTextureWidth, int idleTextureHeight) {
        this.region = region;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
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
    private int frameWidth;
    private int frameHeight;
    private int amountFrames;
    private float frameDuration;
    private int idleTextureX;
    private int idleTextureY;
    private int idleTextureWidth;
    private int idleTextureHeight;

    public String getRegion() { return region; }
    public int getStartRow() { return startRow; }
    public int getStartColumn() { return startColumn; }
    public int getFrameWidth() { return frameWidth; }
    public int getFrameHeight() { return frameHeight; }
    public int getAmountFrames() { return amountFrames; }
    public float getFrameDuration() { return frameDuration; }
    public int getIdleTextureX() { return idleTextureX; }
    public int getIdleTextureY() { return idleTextureY; }
    public int getIdleTextureWidth() { return idleTextureWidth; }
    public int getIdleTextureHeight() { return idleTextureHeight; }
}
