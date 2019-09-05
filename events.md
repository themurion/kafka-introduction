# Rules

Push = Same amount of points for Player + Dealer

- Black-Jack: 3:2 (pay 200, get 300) immediate payout if push is not possible
- Win: 1:1
- Soft 17 for Dealer (+ 1 card if it's Ace + 6)
9
Max Bet: 1000$, Min Bet 5$ (Virtual Money naturally)

Insurance is allowed (half of the initial bet if black jack of dealer is possible, win rate (for the half) is 2:1)

Push: Pay back of original payout

Split: Normal rules (2 cards with same blackjack value can be split into 2 stacks)

## Events

Start Round

- RoundNo: int

CardDealt

- RoundNo: Int
- PlayerNo: Int
- Card: Card

Hit

- PlayerNo: int

Stand

- PlayerNo: Int

CardDealt

- Target: PlayerNo (Int, 0 = Dealer)
- Card: p.e. Heart Jack

Bust

- PlayerNo

## Types

Card

- Type: enum (Hearts (Herz), Diamonds (Karo), Clubs (Kreuz), Spades (Pik))
- Value: enum
