package com.uno.scoreboards.domain.scoreboard.entities;

import com.uno.scoreboards.domain.scoreboard.values.IsWinner;
import com.uno.scoreboards.domain.scoreboard.values.Name;
import com.uno.scoreboards.domain.scoreboard.values.PlayerId;
import com.uno.scoreboards.domain.scoreboard.values.Score;
import com.uno.shared.domain.generic.Entity;

public class Player extends Entity<PlayerId> {
  private Name name;
  private Score score;
  private IsWinner isWinner;

  public Player(PlayerId identity,  Name name, Score score, IsWinner isWinner) {
    super(identity);
    this.name = name;
    this.score = score;
    this.isWinner = isWinner;
  }

  public Player(Name name, Score score, IsWinner isWinner) {
    super(new PlayerId());
    this.name = name;
    this.score = score;
    this.isWinner = isWinner;
  }

  public void addPointsToScore(Integer points) {
    this.score = Score.of(score.getValue() + points);
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

  public IsWinner getIsWinner() {
    return isWinner;
  }

  public void setIsWinner(IsWinner isWinner) {
    this.isWinner = isWinner;
  }
}
