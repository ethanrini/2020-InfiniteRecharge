package com.spartronics4915.frc2020.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SuperstructureCommands
{
    private IndexerCommands mIndexerCommands;
    private IntakeCommands mIntakeCommands;
    private LauncherCommands mLauncherCommands;

    public SuperstructureCommands(IndexerCommands indexerCommands,
        IntakeCommands intakeCommands, LauncherCommands launcherCommands)
    {
        mLauncherCommands = launcherCommands;
        mIntakeCommands = intakeCommands;
        mIndexerCommands = indexerCommands;
    }

    public class LaunchSequence extends SequentialCommandGroup
    {
        public LaunchSequence(int ballsToShoot)
        {
            if(ballsToShoot == 5) {
                addCommands(
                    mLauncherCommands.new WaitForFlywheel(),
                    mIndexerCommands.new LoadToLauncherOptimized()
                );
            } else {
                addCommands(
                    mLauncherCommands.new WaitForFlywheel(),
                    mIndexerCommands.new LoadToLauncher(ballsToShoot)
                );
            }
        }
        /** XXX: should this shoot > 1? */
        public LaunchSequence()
        {
            this(1);
        }
    }

    public class Intake extends ParallelRaceGroup
    {
        public Intake()
        {
            addCommands(
                mIntakeCommands.new Harvest(),
                mIndexerCommands.new LoadFromIntakeWhileIntaking()
            );
        }
    }

    public class IntakeFive extends SequentialCommandGroup
    {
        public IntakeFive()
        {
            addCommands(
                new Intake(),
                new Intake(),
                new Intake(),
                new Intake(),
                new Intake()
            );
        }
    }

}
