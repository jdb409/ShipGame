package com.jon.GameObjects;

import com.jon.AI.AI;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AIControlledShip extends MoveableGameObject {

    private AI ai;

    public AIControlledShip(float x, float y, float width, float height, float speed, AI ai) {
        super(x, y, width, height, speed);
        this.ai = ai;
    }

    @Override
    public void update() {
        ai.update(this);
    }
}
