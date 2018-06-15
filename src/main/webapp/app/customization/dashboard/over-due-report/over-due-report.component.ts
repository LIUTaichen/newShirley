import { Component, OnInit, Input } from '@angular/core';
import { Niggle } from '../../../entities/niggle';
import { NiggleUtilService } from '../../niggle-util.service';

@Component({
  selector: 'jhi-over-due-report',
  templateUrl: './over-due-report.component.html',
  styleUrls: ['./over-due-report.component.css']
})
export class OverDueReportComponent implements OnInit {

  @Input() niggles: Niggle[];
  displayedColumns = ['priority', 'total', 'overdue', 'percentage'];
  dataSource;

  constructor(private niggleUtilService: NiggleUtilService) { }

  ngOnInit() {
    const stats: Stats = this.compute();
    this.dataSource = this.constructDataSource(stats);
  }

  compute(): Stats {
    let highOpen = 0;
    let highOverDue = 0;
    let mediumOpen = 0;
    let mediumOverDue = 0;
    let lowOpen = 0;
    let lowOverDue = 0;
    this.niggles.map((niggle) => {
      if (niggle.status.toString() !== 'OPEN') {
        return;
      }
      if (niggle.priority.toString() === 'HIGH') {
        highOpen++;
        if (this.niggleUtilService.isOverDue(niggle)) {
          highOverDue++;
        }
      }
      if (niggle.priority.toString() === 'MEDIUM') {
        mediumOpen++;
        if (this.niggleUtilService.isOverDue(niggle)) {
          mediumOverDue++;
        }
      }
      if (niggle.priority.toString() === 'LOW') {
        lowOpen++;
        if (this.niggleUtilService.isOverDue(niggle)) {
          lowOverDue++;
        }
      }
    });

    return {
      highOpen,
      highOverDue,
      mediumOpen,
      mediumOverDue,
      lowOpen,
      lowOverDue
    };
  }

  constructDataSource(stats: Stats) {
    const totalOpen = stats.highOpen + stats.mediumOpen + stats.lowOpen;
    const totalOverDue = stats.highOverDue + stats.mediumOverDue + stats.lowOverDue;
    return [
      { priority: 'HIGH', total: stats.highOpen, overdue: stats.highOverDue, percentage: stats.highOverDue / stats.highOpen },
      { priority: 'MEDIUM', total: stats.mediumOpen, overdue: stats.mediumOverDue, percentage: stats.mediumOverDue / stats.mediumOpen },
      { priority: 'LOW', total: stats.lowOpen, overdue: stats.lowOverDue, percentage: stats.lowOverDue / stats.lowOpen },
      { priority: 'TOTAL', total: totalOpen, overdue: totalOverDue, percentage: totalOverDue / totalOpen },
    ];
  }

}
export interface Element {
  priority: string;
  total: number;
  overdue: number;
  percentage: number;
}

export interface Stats {
  highOpen: number;
  highOverDue: number;
  mediumOpen: number;
  mediumOverDue: number;
  lowOpen: number;
  lowOverDue: number;
}
