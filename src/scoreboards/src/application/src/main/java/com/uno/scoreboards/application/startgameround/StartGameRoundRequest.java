package com.uno.scoreboards.application.startgameround;

import com.uno.shared.application.Request;

public class StartGameRoundRequest extends Request {
  protected StartGameRoundRequest(String aggregateId) {
    super(aggregateId);
  }
}
