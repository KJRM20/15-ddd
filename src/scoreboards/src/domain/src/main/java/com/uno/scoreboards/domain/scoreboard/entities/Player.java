package com.uno.scoreboards.domain.scoreboard.entities;

import com.uno.scoreboards.domain.scoreboard.values.Name;
import com.uno.scoreboards.domain.scoreboard.values.PlayerId;
import com.uno.scoreboards.domain.scoreboard.values.Score;
import com.uno.shared.domain.generic.Entity;

public class Player extends Entity<PlayerId> {
  private Name name;
  private Score score;

  public Player(PlayerId identity,  Name name, Score score) {
    super(identity);
    this.name = name;
    this.score = score;
  }

  public Player(Name name, Score score) {
    super(new PlayerId());
    this.name = name;
    this.score = score;
  }

  public void updateScore(Score score) {
    this.score = score;
  }

  public boolean hasReachedTargetScore(Integer targetScore) {
    return score.getValue() >= targetScore;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Score getScore() {
    return score;
  }

  public void setScore(Score score) {
    this.score = score;
  }
}
