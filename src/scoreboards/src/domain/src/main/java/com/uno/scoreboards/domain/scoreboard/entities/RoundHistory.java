package com.uno.scoreboards.domain.scoreboard.entities;

import com.uno.scoreboards.domain.scoreboard.values.RoundHistoryId;
import com.uno.scoreboards.domain.scoreboard.values.RoundList;
import com.uno.shared.domain.generic.Entity;

import java.util.ArrayList;
import java.util.List;

public class RoundHistory extends Entity<RoundHistoryId> {
  private RoundList roundHistoryList;

  public RoundHistory(RoundHistoryId identity, RoundList roundHistoryList) {
    super(identity);
    this.roundHistoryList = roundHistoryList;
  }

  public RoundHistory(RoundList roundHistoryList) {
    super(new RoundHistoryId());
    this.roundHistoryList = roundHistoryList;
  }

  public void addRoundToHistory( RoundHistoryId roundId) {
    List<String> updatedRoundIds = new ArrayList<>(roundHistoryList.getRoundIds());
    updatedRoundIds.add(roundId.getValue());
    roundHistoryList = RoundList.of(updatedRoundIds);
  }

  public RoundList getRoundHistoryList() {
    return roundHistoryList;
  }

  public void setRoundHistoryList(RoundList roundHistoryList) {
    this.roundHistoryList = roundHistoryList;
  }
}
