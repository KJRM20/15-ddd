package com.uno.scoreboards.application.updateplayerspoints;

import com.uno.shared.application.Request;

public class UpdatePlayersPointsRequest extends Request {
  protected UpdatePlayersPointsRequest(String aggregateId) {
    super(aggregateId);
  }
}
